<center><h1>Project Requirements</h1></center>
<ul>
  <li>Vscode</li>
  <li>Ecllipse</li>
  <li>Docker Desktop</li>
  <li>Judge0</li>
  <li>Java21 or above versions is installed in pc.</li>
</ul>

<hr />
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
<hr />

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
