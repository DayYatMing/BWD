select * from crms.agent;
select * from crms.customer;
select * from crms.customer_agents;


select c.short_name 
from customer as c 
left join customer_agents as ca on c.id = ca.customers_id 
left join agent as a on ca.agents_id = a.id 
where  a.login = 'enytBF1ZHIy9hIG8Y8jOpA==';