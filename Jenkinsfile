pipeline {
  agent any
    stages{
      stage('One') {
        steps {
          echo 'Hi, this is Gaurang'
          }
       }
       stage('Two'){
          steps{
            input('Proceed?')
          }
       }
     }
  }
