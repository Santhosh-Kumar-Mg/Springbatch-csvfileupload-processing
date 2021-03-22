# Springbatch-csvfileupload-processing

INSTRUCTIONS TO RUN THE PROJECT

Build:
- Download the project from github and import it as maven project by selecting the pom.xml path (Eclipse or STS(Spring tool suite))
- Use maven build to build the project and required JDK 8 version
- Right click on the project and choose RunAs-> RunConfiguration-> MavenBuild-> NewLaunchConfiguration
- In this section, give some name and paste this in BaseDirectory box-> ${project_loc:csvfileprocessing}
- Then, set the goals as-> 'clean install' and run the build
- Once build completes, you will see Build success message in console

Run:
- To run this project, right click on the project and choose RunAs-> RunConfiguration-> Java Application-> NewLaunchConfiguration
- In this section, give some name and choose the project-> 'csvfileprocessing' and in main class paste this-> com.springbatch.csvfileprocessing.SpringBatchCSVApplication
- Then click on Run button to run the project
- Once server started, you can see the message in console like this-> Tomcat started on port(s): 8080

Execute the project:
- Once serveris started, go to this page to view the UI screen-> http://localhost:8080
- After page loaded, select the sample CSV file given and submit it
- Once batch job finishes it's execution, you can see the success message in UI like this-> Batch job's are executed successfully !!!
- Then, output of the processed files can be seen in this location-> src/main/resources path in the project directory with the name of 'geographic'

NOTE:
- Tried with the sample input given and it took around 20m to complete the job as it is around 120MB file
- The project will run successfully for the fixed columns provided in sample file and not implemented for dynamic columns and not done the validations as well (like other formats pdf, txt and so on)
- The project is designed in such a way that, we can add other format's what we want (For ex: JSON) with minimal code change by just adding the ItemWriter and adding annotations in model class
