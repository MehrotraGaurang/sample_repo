pipeline {
  agent any
    stages{
      stage('One') {
        steps {
          echo 'Hi, this is Gaurang'
          }
       }
       step('Two'){
          steps{
            input('Proceed?')
          }
       }
     }
  }
