#!/bin/sh

# Email details
RECIPIENT="niuday@bw-digital.com"
FROM="no-reply@bw-digital.com"
SUBJECT="Missing PM Sources - Ciena Production - 10.7.2.104"

# File paths
FILE_PATH="/hawaiki_il/ciena"
TXT_FILE="$FILE_PATH/email/email-body.txt"

# Function to print the date and time for logs
print_date_time() {
    datenow=$(date +%d/%m/%Y~%H:%M:%S)
    echo "$datenow - $1"
}

# Function to send email with the generated HTML content
send_email() {
    sendTo=$1
    messageFile=$2
    print_date_time "Testing"
    message=$messageFile
    print_date_time "$message"

    # Construct email headers
    {
        echo "To: $RECIPIENT"
        echo "From: $FROM"
        echo "Subject: $SUBJECT"
        echo "MIME-Version: 1.0"
        echo "Content-Type: text/html; charset=UTF-8"
        echo "$message"
        
    } | sendmail -t || { echo "sendmail failed"; exit 1; }
}

# Get the current day of the month dynamically
current_day=$(date +%d)
print_date_time "Current day of the month: $current_day"

# Send the email 
send_email "$RECIPIENT" "$TXT_FILE"

print_date_time "Summary report sent successfully."
