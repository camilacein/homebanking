const { createApp } = Vue

const options = {
  data() {
    return {
      email:"",
      password:"",
      registered:false,
      name:"",
      lastname:"",
    
    } // finaliza return
  }, // finaliza data
  created() {
    console.log(this.registered)
    console.log(this.email)
    console.log(this.password)
  }, //finaliza created

  methods: {
    login(){
      axios.post("api/login?email=" +this.email +"&password="+this.password)
        .then(response => {
            console.log(response)
            if (this.email == "ceincamila@gmail.com" && this.password == "1234") {
              window.location.href = "./admin/create-loan.html"
          } else {
              window.location.href = "/assets/accounts.html"
          }

          this.clearData()
          
        })
        .catch(error => console.log("Error", error))
    },
    register(){
        axios.post("api/clients?name=" + this.name 
                            + "&lastname=" + this.lastname
                            + "&email=" + this.email 
                            + "&password=" + this.password)
          .then(response => {
            console.log(response)
            this.login()
          })
          .catch(error => console.log("Error", error))
      },
    clearData(){
      this.email = ""
      this.password = ""
      this.name = ""
      this.lastname = ""
    },
    
  
    updateEmail(event) {
      this.email = event.target.value;
      console.log(this.email)
    },
    updatePassword(event) {
      this.password = event.target.value;
      console.log(this.password)
    },
    updateName(event){
      this.name = event.target.value;
      console.log(this.name)
    },
    updateLastname(event){
      this.lastname = event.target.value;
      console.log(this.lastname)
    },
    createAccount(){
      axios.post("/api/clients/current/accounts")
      .then(response =>console.log(response))
      .catch(error => console.log(error))
  },
  }}

const app = createApp(options)
app.mount('#app')