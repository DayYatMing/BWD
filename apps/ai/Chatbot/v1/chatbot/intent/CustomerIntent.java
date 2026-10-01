package com.bwd.chatbot.intent;

import com.bwd.chatbot.db.DBProcessor;

public class CustomerIntent {

    public String getCustomer(){
        DBProcessor dbp = new DBProcessor();
        String query = "SELECT name FROM pm_db.customer";
        String[] customers = dbp.getCustomers(query);
        StringBuilder sb = new StringBuilder();
        for(String customer : customers){
            sb.append(customer + "\n");
        }

        return "This is customer list: \n" + sb;
    }

}
