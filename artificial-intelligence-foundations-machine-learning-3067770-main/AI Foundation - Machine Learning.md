supervise learning
linear regression
be off putting


Feature that predict selling price
Location
Age
no. of bed room
Ocean proxmimity
to predict what that home sell for


classified or identified flower in an image

feed a maching image of flower doing training process to identify  dandelions, sunflowers, daisies, tulips and roses


Supervised learning
crime in uk
UK => four country => England, Scotland, Wales, Nothern Island, 
solving a binary classification problem this time
- Stop and Search Data
 - Location
 - Gender
 - Ethnicity
 - Time of day 
Whether or not release


Machine Learning since 1950
- subset of AI that applied math to large datasets to find trends and patterns while mapping input and output
- age, location, no. bed room => what will selling price be?

Mapping uncovered bet input and output are stored in math model, simply called model
- Models are depicted as brain => meant to simulate human intelligence
- mass

-Machine Learnng wide separated coz of
big data, easier acces to large datasets needed to train maching learning model and cloud which bring facilities with easy to use machine learning services
and access to powerful compute power

- Linear Algebra
- Basic Equation
- Probability

## 3 common Maching Learning Techniques
- Reinforcement learning
- Supervised Learning
- Unsupervised Learning

### Supervised Learning
- trains model / machine with labeled data
- knows i/p and o/p data
- teach machine to predict future o/p as new data comes in

#### Labeled Data
- contains target values the machine learns to predict
- target is medium house value, the machine reviews feature(variable) to predict the target

#### Linear regression
- ML algorithm based on supervised learning and is mostly used to find
 the relationship between variables for Forecasting
 
Ex
- Cost of home in California 
- machine trained on datasets, includes
 - Latt and long
 - Age
 - Rooms
 - Population
 - Households
 - Median income
 - Ocean Proximity - location of house in proxmimity to the ocean
 - Value
 - Medium house value - what that home is worth, targer variable( want the machine to learn how to predict)
#### regression problem
machine stuies the labeled data to find patterns and learn how to predict home values so called regression problem coz
it predicts a number or continuous value in mathematics


#### Classification Techniques
- predicts discrete responsess (eg. yes or no values)
- data tagging, categorization or separated into groups or classes
- Flower identification Case Study - Multi-class label classification coz we will train model to tag the type of flower found in image

#### Unsupervised learning
- doesn't use labled data, instead it groups/interpret data based only on i/p data
- clustering is comon unsupervised learning techniques
- the hospital industry often clusters or segment customers which allow them to tailor their marketing strategy to gain loyal 
customer and competitive advantage


#### Reinforcement Learning
- allow machine to learn through trial and error learning
- doesn not rely on defined dataset
- operate in dynamic env
- learn through experience
eg, train a dog with positive and negative reinforcement learns - behavior that will get is most treats
- robotic chess player,  robot


LLM - Large Language Model and generative AI

Traditional Machine Learning
- Regression
- Decision Trees
- Random Forests
- Clustering

Use case
- Fraud Detection, Customer churn prediction, Inventory Management, Demand FOrecasting
- ML come in shine - Focused, Efficient, Grounded in data driven insights

Simply Models
- Train Faster
- Cost less to maintain
- Deployed anywhere

Interpretability
- mean you can explain why a model made a specific prediction

ML Foundation
- Gathering data
- Cleaning it
- Training Models
- Evaluating Performance
- Improving overtime

ML - Deep Learning - Generative AI


ML life cycle
Standard stages to follow

SDLC Stages
- Problem info and understanding
 - explore current business process to identify where maching learning adds value
 - is ML an ethical solution to the problem?
 - whar are i/p and o/p?
 - What prediction error rates will you accept?
 - What do you required for accuracy perpective
- Data collection and preparation
 - Souce the data you needed => internal data sources, provided data, open source or public datastore, purchased data
 - raw data is not in a state that a machine can learn from
 - Spend time annotating and wrangling the data
 - Label data
 - Remove irrelevant features
 - Toss outliers
 - Transform data
 - input missing values
- Model training and testing
Once the data is ready split the process into 3 data subset => training (80%), validating(10%), testing data(10%)
After select the data, select ML algorithm and point it to your process data to start training process
iterate and experiment, fine tue, evaluate,  until you land well performing model that you place your model into production
- Model deployment and maintenance
 - once model is ready setup cadence for retraining, monitoring overall performance


ML to consider
- predict outcome
- Uncover trends and patterns in data
- Have rules and steps that can't be coded
- dataset is too large to process by human

Frame your problems
- Define the questions for your model
- define required inputs and expected output
- eg. yes or no => binary classification problems/ algorithm (AWS Linear learner)
- expecting numeric or continuous value outputs try the XGBoost algorithm.
- Algorithm
- Data set
- Identity relationships bet each feature
- Identity how each feature will predict the target
- eg. home selling Price Prediction Features
 - school district, no. of bedrooms, age, day of week sold, time of day sold
- think how the model's output integrate into existing apps?

ML problem framing is important step in the development of any machine learning system
Why is framing your machine learning problem an important step??
because machine learning is not a good solution to every problem	
Machine learning is not the solution to every problem, and when used incorrectly, it can cause more harm than good
- determine long terms cost and maintenance to run your model in prod

- Train model from scratch
- use your own data or use pretrained model
- speedup your development cycle
- use similar benchmark dataset
- why not want to use own dataset?
- obtaining enough data to achieve good performance is a big challenge,
- if there is a model already on market that solves your business problem, why not make use of it? (Save time and money)	

### Image classification problems
- use pre-built model
- use a process called transfer learning, allow you to add your data on top of pre-trained model, allow you to train new model that inherit learning from previous model

### where can find pre-trained model?
Model zoo is most popular https://modelzoo.co/
- edu purpose
- transfer learning
- other uses
- Apache 2.0 license, sw developers are free to alter the code, copy code, update source code
- AWS pre-trained model https://aws.amazon.com/marketplace/solutions/machine-learning/pre-trained-models
- https://huggingface.co/
- Sentiment analysis
- Q & A
- lang translation
- text summarization

## What if pre-trained modesl won't solve the problems?
- data is critical element of any ML project
- Select a large enough data set
- have enough compute processing power
- need hardware to support computationally intensive processes.
- simple model, training on local machine
- training custom model require writing code 
- No code option
- Code: R, python (most popular to train model), and Java

## Python based data analytics
- Pandas (Solid data structure, N dimenstional matrices, perform exploratory data analysis, easy to read CSV, JSON, and TSV data files), 
- Numpy ( Numericcal computing, multi-dimenstional arrays and matrices
- Matplotlib, Seaborn ( plotting charts and graph)

## ML framework lib
- Scikit-learn, Tensor Flow, MXNet, PyTorch, Keras

Python, Jupyter, Scikit-learn, Numpy, Pandas, Matplotlib


When should you train a custom model?

A. The dataset is small in size.
B. The dataset is large.
C. Powerful compute power is easily accessible.
D. No pre-trained models on the market meeting your needs.
B, C, D

Training a custom model makes sense when there are no pre-trained models on the market, you have enough data and the needed compute processing power.


Which phase of the machine learning lifecycle helps you explore your current business processes to identify where machine learning adds value?
Problem Formation & Understanding
During this stage, you explore the current business processes to identify where machine learning adds value.


Why do you spend time processing and preparing your data during the machine learning lifecycle?
The raw data is usually never in a state a machine can learn from.
The data you collect, called raw data, is usually never in a state that a machine can learn from.


Why do you split your data into training, validation, and testing?
so the model can be tested on data it hasn't seen before
This split is important so your model can be validated and tested on data it wasn't trained on.





Why would you not want to use your data on a machine learning project?

A. You don't have enough data.
B. You don't have the resources to process/clean data.
C. Your dataset is too large to process.
D. Your data needs additional processing time to clean and transform it.
A,B
You need a lot of data to achieve good performance. If you have enough data but not the resources to clean it, then data should be sourced from elsewhere.

Data is critical element in any ML project
### Data source
- Internal datastore
- Client provided Data
- Open-source
- Public datasource

### Data use to Predict home costs
- income
- Population
- House occupancy
- Location
- Number of bedrooms
- Number of Rooms
- Age
- will use supervised learning Linear regression



https://www.linkedin.com/learning/artificial-intelligence-foundations-machine-learning-22345868/obtaining-data?autoSkip=true&contextUrn=urn%3Ali%3AlearningCollection%3A7425886518646550528&resume=false&u=116771770