<center><h1>Summary</h1></center><br/>
Our project name is GOQuiz, which is a secure online quiz system for students and faculty.
This system helps faculty create quizzes, schedule exams, and download results easily.
Students can login, attend quizzes, and view their scores in dashboard.
To prevent cheating, our system has auto-submit feature when students switch tabs.
To avoid answer loss during network problems, we used Apache Kafka for quiz continuity.
We also added coding quiz support using Judge0, which allows students to write and run programs during exams.
The frontend is developed using React, backend using Spring Boot, and database using MongoDB.
We used Docker to run services easily.
This project makes online exams secure, reliable, and easy to manage for both students and faculty.


<center><h1>Project Requirements</h1></center>
<ul>
  <li>Vscode</li>
  <li>Ecllipse</li>
  <li>Mongodb</li>
  <li>Docker Desktop</li>
  <li>Judge0</li>
  <li>Java21 or above versions is installed in pc.</li>
</ul>


<center><h1>Setup Project</h1></center>
<ul>
  <li>
    Install Desktop Docker through the website  <a href="https://docs.docker.com/"><u>Click</u></a>
  </li>
  <li>
    Clone Judge0 in your PC or Laptop by using following steps:
    <ul>
      <li>open the cmd in the pc and choose either c nor d drive path.</li>
      <li>execute the command -> "git clone https://github.com/judge0/judge0"</li>
      <li>open the docker desktop app</li>
      <li>open the cmd in the pc and select path where judge0 is installed upto c:/ ... /judge0/judge0 (or) d:/../judge0/judge0</li>
      <li>execute the command for start running judge0 "docker-compose up -d"</li>
      <li>execute the command for stop running judge0 "docker-compose down -v"</li>
    </ul>
  </li>
  <li>
    clone the project repo by using follwing steps:
    <ul>
      <li>open the new terminal or cmd and choose either c or d drive path.</li>
      <li>execute the command to clone project -> "git clone https://github.com/harish151/SVEC-Quizexamination-Portal.git"</li>
    </ul>
  </li>
</ul>

<ul># use the following commands to import data to localmongodb: <br/>
     <li>mongoimport --uri mongodb+srv://harish151:Harish_151@cluster1.rcsq9om.mongodb.net/Cluster1 --collection <COLLECTION> --type JSON --file Cluster1 </li>
     <li>mongoexport --uri mongodb+srv://harish151:Harish_151@cluster1.rcsq9om.mongodb.net/Cluster1 --collection <COLLECTION> --type JSON --out Cluster1 </li>
     <li>mongodump --uri "mongodb+srv://harish151:Harish_151@cluster1.rcsq9om.mongodb.net/Cluster1" --out C:\Users\user\Documents\dumpAtlas </li>
     <li>mongorestore --uri "mongodb://localhost:27017" "C:\Users\user\Documents\dumpAtlas" </li>
</ul>


<center><h1>Execution Steps</h1></center>
<ul>
  <li>Step-1: open docker desktop app</li>
  <li>Step-2: open cmd in pc and go to path where judge0 is installed upto ../judge0/judge0 and run the command "docker-compose up -d"</li>
  <li>Step-3: open new cmd in pc and go to path where project is installed upto ../SVEC-Quizexamination-Portal and execute the command "docker-compose up -d"</li>
  <li>Step-4: setup backend in java execution tool like ecllipse,intellij,vscode: (prefer ecllipse to better execution)</li>
  <ul>
    <li>First, import "GoQuiz-Backend" folder in ecllipse.</li>
    <li>import "Compiler-Backend" folder in ecllipse.</li>
    <li>now run main class files in both the folders one by one.</li>
  </ul>
  <li>Step-5:</li>
  <ul>
    <li>open the Frontend folder in execution tool like vscode.</li>
    <li>open terminal or cmd in vscode and set path upto ../Frontend </li>
    <li>run the command to install dependicies - "npm install" (only once after setup project in pc).</li>
    <li>run the command to run react.js project - "npm run dev" (open the geeing url to view UI).</li>
  </ul>
  <li>Step-6: After terminated the running project. then open the judge0 and project paths in cmd and down the docker using "docker-compose down -v" </li>
</ul>
