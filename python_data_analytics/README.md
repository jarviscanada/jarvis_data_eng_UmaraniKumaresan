# Introduction  

London Gift Shop (LGS), an online retailer in the UK specializing in giftware, has faced stagnant revenue growth despite being in business for over ten years. To tackle this issue, the LGS marketing team is turning to data analytics to better understand customer behavior and improve their marketing efforts. Given their limited IT resources, LGS has partnered with Jarvis Consulting to support their data engineering and analytics needs.

The project aims to examine LGS’s transactional data to uncover patterns in customer shopping behavior. These insights will empower the marketing team to execute more targeted strategies, such as personalized discounts, event-based promotions, and tailored email marketing campaigns.  

# Technologies Used:
- Python was the primary language used for data processing and analysis.

- SQL was used to extract and query transactional data from the database.

- Jupyter Notebook provided an interactive environment for data exploration and analysis.

- NumPy was employed for numerical computations and operations.

- Pandas handled tasks related to data cleaning, manipulation, and transformation.

- Matplotlib and Seaborn were utilized to generate insightful visualizations of the data.

# Implementation  

## Project Architecture  
London Gift Shop’s web app is hosted on Microsoft Azure, with the front end delivered via Azure CDN and the back end running on microservices through Azure Kubernetes Service (AKS), managed by API Management. Transactional data is stored in a SQL Server OLTP database.
For this proof of concept, a sample of the data was extracted and moved to a PostgreSQL database for analysis using Jupyter Notebooks.


# Data Analytics and Wrangling  

## Jupyter Notebook Analysis  

To address LGS's revenue stagnation, the analysis in this PoC focused on:  

- Customer Behavior Insights: Analyzing patterns in user activity to better understand customer interactions.

- RFM Segmentation: Classifying customers based on Recency, Frequency, and Monetary value to personalize marketing efforts.

- Monthly Trends Analysis: Examining sales trends, customer growth, and order volumes to identify key patterns over time. 

These insights enable LGS to assess the effectiveness of past marketing campaigns, understand customer segments, and refine advertising strategies for better engagement and conversion.  

# Improvements  

To further enhance this analysis, the following improvements could be made:  

- Customer Lifetime Value (CLV) Segmentation: Estimate the long-term revenue potential of customers to refine targeting approaches.

- Refined RFM Segmentation Analysis: Analyze specific customer segments from the RFM model to optimize engagement strategies.
