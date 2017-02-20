Shutterfly Customer Lifetime Value

One way to analyze acquisition strategy and estimate marketing cost is to calculate the Lifetime Value (“LTV”) of a customer. Simply speaking, LTV is the projected revenue that customer will generate during their lifetime.

A simple LTV can be calculated using the following equation: 52(a) x t. Where a is the average customer value per week (customer expenditures per visit (USD) x number of site visits per week) and t is the average customer lifespan. The average lifespan for Shutterfly is 10 years.

Code Requirements

Write a program that ingests event data and implements one analytic method, below. You are expected to write clean, well-documented and well-tested code. Be sure to think about performance - what the performance characteristic of the code is and how it could be improved in the future.

You may use one of the following OO languages: Java, Python, Scala.

Ingest(e, D)

Given event e, update data D

TopXSimpleLTVCustomers(x, D)

Return the top x customers with the highest Simple Lifetime Value from data D.

Events

Please use the following sample events the Data Warehouse collects from Shutterfly’s public sites. All events have a key and event_time, but are received with no guaranteed order and with fluctuating frequency.

See sample_input directory for a sample of each event.

Customer

type *
CUSTOMER
verb *
NEW
UPDATE
Additional Data
key(customer_id) *
event_time *
last_name
adr_city
adr_state
Site Visit

type *
SITE_VISIT
verb *
NEW
Additional Data
key(page_id) *
event_time *
customer_id *
tags (array of name/value properties)
Image Upload

type *
IMAGE
verb *
UPLOAD
Additional Data
key(image_id) *
event_time *
customer_id *
camera_make
camera_model
Order

type *
ORDER
verb *
NEW
UPDATE
Additional Data
key(order_id) *
event_time *
customer_id *
total_amount *
* represents required data

Directory structure

This project has the following directories, and it is expected that your code will follow the same structure.

- README.md
- src (your source will live here)
- input (input file you create as proof of functionality lives here)
- output (output file with the result of TopXSimpleLTVCustomers(10, D). Include the calculated LTV.)
- sample_input (one event of each type for visualization purposes)
Reference

https://blog.kissmetrics.com/how-to-calculate-lifetime-value