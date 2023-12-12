## Run the programme
In order to run the programme just run the src/main/Main.java file. Don't forget to set up launch.json file to import javafx and to include files from lib to the java Project/Referenced Libraries (if running in VS Code). 

## Folder Structure

The workspace contains two folders , where:

- `src`: the basic foulder with our java files, here the Main is situated. 
- `lib`: the folder to maintain dependencies 
- `data`: the folder with our data



## Our plots
1. Histograms and count plots. 
x-axis shows the range of values of this variable
y-axis shows the amount of the object with this exact value

You can choose datasets and variables to show. 

2. Scatter plots. 
It shows the basic scatterplot, where the size of the dot means the amount of the objects with the same exact x-axis and y-axis value. The size is normalized. 
There is also a regression line. 

You can choose dataset and 2 courses to make scatter plot with. 

3. Joint plots (with bar chart or with line chart)
Due to the limitations of the javafx and specifics of our data (no contionous variables), we made joint plots not with distributions but with barchart/line chart, and it is just shown above the x-axis. If you want to chedck the distribution of the choosen categorical property for y-axis just press Switch axes.

You can choose 2 courses and 1 categorical property. Only Current Grades are used in this plot beacause only they have StudentInfo proprties.

4. Pie-chart with cum-laude students
You can choose the conditions for cum-laude (what is the boundary value and what is the type, no grades lower than x or gpa better than x)

5. Courses difficulty count plots 
You can choose Ascending/Descending order and a dataset. y-axis - amount average grade per course. 

6. Courses similairity 
There are correlation, cosine similarity, euclidian distance (weighted ) tables. They need time to upload, so please be patient, they will show up!


7. Grade Average for Groups of Students by Student Property
In order to run just select courses with ctrl (you can select all). It is important to first choose the property and then courses!

8. Number of missing grades per student

In order to run just select courses with ctrl (you can select all). You can be shown grades for comparison. 

9. Ungraded Students count per ourse
In order to run just select courses with ctrl (you can select all). You can be shown grades for comparison. 

10. Sorted Ungraded Student Count per course
Just a histogram. 

11. Swarm plot 
It is used for showing the distribution of grades in the course splitted by some property. 
When you are choosing course property it automatically finds the best split by the choosen property and shows it. When you are choosing categorical variable it is splitted in n categories where n is the amount of unique values of the choosen property.
x-axis variable - variable which we are splitting by (select it at the top), y - axis - variable which we show distribution of (select it at the bottom).
You can choose Best stump for this course (stump which splits our course, selected at the top, the best).

12. Prediction accuracy 
Table with prediction accuracy (weighted euclidian distance between the actual grades and our predictions for them). There are 3 courses we were predicting grades for and there are 3 models (with 10 best splits which are averaged, with 3 best splits which are averaged and solo best split). 



