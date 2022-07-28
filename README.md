# Spring Boot ETL datawarehouse application

The application is a simple Spring Boot ETL - warehouse application
Two controllers (CSV Conroller, Warehouse Controller) have been implemented, the data store is MYSQL database

- CSV Conroller for uploading, downloading, get all rows of CSV file (You can use the Swagger e.g http://localhost:8080/swagger-ui.html)

- Warehouse Contoller for querying and load data against the database (via JPA layer)
It supports aggregate functions

You can find below a sample JSON object for performing such an action.

It is build as requested
- a set of metrics (plus calculated ones) to be aggregated on (metrics array)
- an optional set of dimension filters to be filtered on (filters array)
- an optional set of dimensions to be grouped by (groups array)
 
The Aggregate function should be a valid JPA function (like SUM, AVG etc)

{
  "metrics": [
    {
      "metric": "datasource"
    },
    {
      "metric": "SUM(clicks)",
      "alias": "totalclicks"
    }
  ],
  "filters": [
    {
      "key": "datasource",
      "operator": "=",
      "value": "Google Ads"
    },
    {
      "key": "daily",
      "operator": ">",
      "value": "2020-01-01"
    },
    {
      "key": "daily",
      "operator": "<",
      "value": "2021-01-01"
    }
  ],
  "groups": [
    {
      "group": "datasource"
    }
  ]
}    

TODO (improvements)
- support for OR operators
- support sorting of results
- possible better solution (more clean) the criteria JPA API

 

