# London Gift Shop Retail Analytics with PySpark

## Project Overview
After completing the analysis of London Gift Shop's retail data, the Data Engineering team was tasked with improving the data solutions architecture to support larger datasets. The project moved from a single-machine setup using Python/pandas to a more scalable solution based on Apache Spark, deployed on a cluster via Azure Databricks. This shift enabled the processing of significantly larger datasets. The ETL and analysis approach remained the same as in the previous pandas implementation, allowing for a direct comparison between pandas/matplotlib and Spark within a cluster. The team also assessed the benefits and drawbacks of using Scala versus PySpark, as both are compatible with Spark. The aim of the project was to extract customer insights from over 1 million synthetic transaction records, focusing on metrics such as customer growth, sales trends, and customer segmentation (RFM), all implemented using PySpark in Databricks.
## Technologies Used
- Azure Blob Storage and Databricks File System (DBFS) for file storage.
- Hive Metastore for data cataloging and table persistence.
- PySpark (Databricks Notebooks) for ETL, aggregation, and analytics.
- Apache Spark Structured APIs for data processing.

## Databricks PySpark Implementation

This end-to-end pipeline processes raw retail data and delivers actionable insights via transformations and group-level analytics in Spark.

### Data Wrangling & Cleansing:
The data was preprocessed by renaming columns and enforcing appropriate schema types to ensure consistency. Derived metrics, such as total_amount, were created to support analytical insights. Additionally, invoice IDs were cleaned, and invalid or incomplete rows were filtered out to improve data quality and reliability.

## Architecture

- Storage: The dataset is securely stored in Azure Blob Storage and accessed through Databricks File System (DBFS).

- Compute: A Databricks cluster with autoscaling enabled is used for processing large datasets efficiently.

- Processing: Data is cleansed, transformed, and analyzed using PySpark, specifically leveraging Spark SQL and DataFrame APIs.

- Visualization: Insights are visualized directly in Databricks notebooks using the built-in display() function.

- Cataloging (Optional): Processed tables can be registered in the Hive Metastore for persistence and easier querying.


            +---------------------------+
            |   Azure Blob Storage      |
            | - Raw Dataset Storage     |
            +---------------------------+
                        |
                        v
            +---------------------------+
            |     Databricks Platform   |
            |  (PySpark on Cluster)     |
            | - Data Cleansing          |
            | - Transformations         |
            | - Analytics (SQL, RFM)    |
            +---------------------------+
                        |
                        v
            +---------------------------+
            |  Hive Metastore (Optional)|
            | - Table Registration      |
            | - Schema Management       |
            +---------------------------+
                        |
                        v
            +---------------------------+
            | Databricks Notebook UI    |
            | - Visualizations (`display()`) |
            | - Exploration & Insights   |
            +---------------------------+
## Key Analytics Performed
The analysis focused on several key metrics to gain customer and sales insights. Monthly Sales measured the total transaction revenue each month, while Sales Growth tracked the percentage change in revenue compared to the previous month. Monthly Active Users represented the count of unique customers engaging each month, and New vs Existing Users differentiated between first-time and returning customers. RFM Segmentation was used to assess customer lifetime value by evaluating Recency, Frequency, and Monetary value. Additionally, Invoice Outlier Distribution involved cleaning and visualizing invoices that fell outside the 85th percentile to detect anomalies and unusual transaction patterns.
## Future Improvements

- Conduct Customer Lifetime Value (CLV) segmentation to estimate future revenue from customers and enhance targeting strategies.
- Carry out A/B testing following targeted campaigns to collect additional data and enable cluster analysis.
Further analyze specific customer groups identified through RFM segmentation for deeper insights.
- Analyze trends at the SKU level to identify patterns and potential cross-selling opportunities.
- Apply clustering techniques like K-Means or machine learning models to refine customer behavior segments beyond traditional RFM analysis.
