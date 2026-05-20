#!/bin/sh

# Email details
RECIPIENT="niuday@bw-digital.com"
BCC_EMAIL="niuday@bw-digital.com"
FROM="niuday@bw-digital.com"
SUBJECT="Endpoint Health Status Summary"

# File paths
FILE_PATH="/hawaiki_il/endpointcentral"
CSV_FILE="$FILE_PATH/datacollection.csv"

# Function to print the date and time for logs
print_date_time() {
    datenow=$(date +%d/%m/%Y~%H:%M:%S)
    echo "$datenow - $1"
}

# Function to send email with the generated HTML table
send_email() {
    sendTo=$1
    message="<br>"
    message="$message$2"
    echo "***** Sending summary report *****"
    echo "$message" | mail -s "$SUBJECT" -r "$FROM" -a "Content-Type: text/html" -b "$BCC_EMAIL" "$RECIPIENT"
}

# Prepare the HTML body for the email
HTML_BODY="<html><body>
<p>Dear BW Team,</p>

<p>Please find below the summary of your endpoint health status for this week:</p>
<table border='1' cellpadding='5' cellspacing='0'>
<tr><th>Endpoint Name</th><th>Healthy Days (Week)</th></tr>"

# Read the data from the CSV and append it to the HTML table for the week
print_date_time "Reading data from processed CSV..."
while IFS=";" read -r endpoint_name healthy_days_week healthy_days_month
do
    # Skip header (if any)
    if [ "$endpoint_name" = "endpoint_name" ]; then
        continue
    fi
    
    # Add row to the weekly health status table
    HTML_BODY="$HTML_BODY<tr><td>$endpoint_name</td><td>$healthy_days_week</td></tr>"
done < "$CSV_FILE"

# Closing the weekly health status table
HTML_BODY="$HTML_BODY</table>"

# Adding the summary for the current month
HTML_BODY="$HTML_BODY
<p>Additionally, here is the summary for the current month:</p>
<table border='1' cellpadding='5' cellspacing='0'>
<tr><th>Endpoint Name</th><th>Healthy Days (Month)</th></tr>"

# Read the data from the CSV and append it to the HTML table for the month
print_date_time "Reading data for monthly health status..."
while IFS=";" read -r endpoint_name healthy_days_week healthy_days_month
do
    # Skip header (if any)
    if [ "$endpoint_name" = "endpoint_name" ]; then
        continue
    fi
    
    # Add row to the monthly health status table
    HTML_BODY="$HTML_BODY<tr><td>$endpoint_name</td><td>$healthy_days_month</td></tr>"
done < "$CSV_FILE"

# Closing the monthly health status table
HTML_BODY="$HTML_BODY</table>"

# Add reminder message at the end
HTML_BODY="$HTML_BODY
<p>As a reminder, keeping your IT assets up to date is a key part of your responsibilities. 
While we automate most patching processes, some updates require a manual reboot, which only you can perform. 
Taking this action promptly helps reduce our overall security risks and greatly supports the team's efforts in safeguarding our digital environment.</p>

<p>Thank you for your continued cooperation.</p>
<hr>
<p>Best regards,</p>
<p>Your IT Team</p>
</body></html>"

# Send the email with the HTML body
send_email "$RECIPIENT" "$HTML_BODY"

print_date_time "Summary report sent successfully."
