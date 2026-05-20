#!/bin/sh

# Email details
RECIPIENT="niuday@bw-digital.com"
FROM="no-reply@bw-digital.com"
SUBJECT="Endpoint Health Status Summary"

# File paths
FILE_PATH="/hawaiki_il/endpointcentral"
CSV_FILE="$FILE_PATH/datacollection.csv"

# Function to print the date and time for logs
print_date_time() {
    datenow=$(date +%d/%m/%Y~%H:%M:%S)
    echo "$datenow - $1"
}

# Function to send email with the generated HTML content
send_email() {
    sendTo=$1
    message=$2

    # Construct email headers
    {
        echo "To: $RECIPIENT"
        echo "From: $FROM"
        echo "Subject: $SUBJECT"
        echo "MIME-Version: 1.0"
        echo "Content-Type: text/html"
        echo
        echo "$message"
    } | sendmail -t
}

# Get the current day of the month dynamically
current_day=$(date +%d)
print_date_time "Current day of the month: $current_day"

# Prepare the HTML body for the email
HTML_BODY="<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 20px;
            background-color: #f9f9f9;
        }
        h2 {
            color: #2e6da4;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 8px 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .footer {
            font-size: 14px;
            color: #777;
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
    <p>Dear BW Team,</p>
    <p>Please find below the summary of your endpoint health status for this month:</p>
    
    <!-- Monthly Health Status -->
    <table>
        <thead>
            <tr>
                <th>Endpoint Name</th>
                <th>Healthy Days (Month)</th>
            </tr>
        </thead>
        <tbody>"

# Read the data from the CSV and append it to the HTML structure for the month
print_date_time "Reading data for monthly health status..."
while IFS=";" read -r endpoint_name healthy_days_week healthy_days_month
do
    # Skip header (if any)
    if [ "$endpoint_name" = "endpoint_name" ]; then
        continue
    fi
    
    # Remove leading/trailing spaces
    endpoint_name=$(echo "$endpoint_name" | xargs)
    healthy_days_month=$(echo "$healthy_days_month" | xargs)

    # Calculate the percentage based on the current day as the total days
    healthy_days_month_int=$(printf "%.0f" "$healthy_days_month")  # Round to an integer
    if [ "$current_day" -gt 0 ]; then
        health_percentage=$(echo "scale=2; ($healthy_days_month_int / $current_day) * 100" | bc)
    else
        health_percentage=0
    fi
    
    # Add row to the monthly health status section
    HTML_BODY="$HTML_BODY
        <tr>
            <td>$endpoint_name</td>
            <td>$healthy_days_month_int ($health_percentage%)</td>
        </tr>"
done < "$CSV_FILE"

# Closing the monthly health status section
HTML_BODY="$HTML_BODY</tbody></table>"

# Add reminder message at the end
HTML_BODY="$HTML_BODY
<p>As a reminder, keeping your IT assets up to date is a key part of your responsibilities. 
While we automate most patching processes, some updates require a manual reboot, which only you can perform. 
Taking this action promptly helps reduce our overall security risks and greatly supports the team's efforts in safeguarding our digital environment.</p>

<p>Thank you for your continued cooperation.</p>
<hr>
<p>Best regards,</p>
<p>Your IT Team</p>
<div class='footer'>
    <p>If you have any questions or need assistance, feel free to contact us.</p>
</div>
</body>
</html>"

# Send the email with the HTML body
send_email "$RECIPIENT" "$HTML_BODY"

print_date_time "Summary report sent successfully."
