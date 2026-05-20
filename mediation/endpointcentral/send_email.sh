#!/bin/sh

# Database connection details
db_ip=10.10.13.201
db_name=pm_db
db_port=3306
db_user=mediation_rw
db_pass=PqTLyM41TjEuiTfF

# Email details
RECIPIENT="niuday@bw-digital.com"
BCC_EMAIL="niuday@bw-digital.com"
FROM="niuday@bw-digital.com"
SUBJECT="Endpoint Health Status Summary"

# Query the database for endpoint health data for the week and the month
QUERY="SELECT 
    endpoint_name,
    SUM(CASE WHEN YEARWEEK(date, 1) = YEARWEEK(CURDATE(), 1) THEN healthy ELSE 0 END) AS healthy_days_week,
    SUM(CASE WHEN MONTH(date) = MONTH(CURDATE()) AND YEAR(date) = YEAR(CURDATE()) THEN healthy ELSE 0 END) AS healthy_days_month
    FROM pm_db.endpointcentral
    WHERE date >= CURDATE() - INTERVAL 1 YEAR 
    GROUP BY endpoint_name
    ORDER BY healthy_days_week DESC, healthy_days_month DESC;"

# Fetch the data from the database and format it as an HTML table
HTML_BODY="<html><body>
<p>Dear BW Team,</p>
<p>Please find below the summary of your endpoint health status for this week:</p>
<table border='1' cellpadding='5' cellspacing='0'>
<tr><th>Endpoint Name</th><th>Healthy Days</th></tr>"

# Fetch data from the database and loop through it to populate the table rows
mariadb -u $db_user -p $db_pass -h $db_ip -D $db_name -se "$QUERY" | while IFS=$'\t' read -r endpoint_name healthy_days_week; do
    HTML_BODY="$HTML_BODY<tr><td>$endpoint_name</td><td>$healthy_days_week</td></tr>"
done

# Add closing HTML tags for the table and email
HTML_BODY="$HTML_BODY</table>
<p>Additionally, here is the summary for the current month:</p>
<table border='1' cellpadding='5' cellspacing='0'>
<tr><th>Endpoint Name</th><th>Healthy Days</th></tr>"

# Fetch the data again for the month (same query can be used)
mariadb -u $db_user -p $db_pass -h $db_ip -D $db_name -se "$QUERY" | while IFS=$'\t' read -r endpoint_name healthy_days_month; do
    HTML_BODY="$HTML_BODY<tr><td>$endpoint_name</td><td>$healthy_days_month</td></tr>"
done

# Add closing HTML tags
HTML_BODY="$HTML_BODY</table>

<p>As a reminder, keeping your IT assets up to date is a key part of your responsibilities. 
While we automate most patching processes, some updates require a manual reboot, which only you can perform. 
Taking this action promptly helps reduce our overall security risks and greatly supports the team's efforts in safeguarding our digital environment.</p>

<p>Thank you for your continued cooperation.</p>
<hr>
<p>Best regards,</p>
<p>Your IT Team</p>
</body></html>"

# Send the email using the 'mail' command
echo "$HTML_BODY" | mail -s "$SUBJECT" -r "$FROM" -a "Content-Type: text/html" -b "$BCC_EMAIL" "$RECIPIENT"
