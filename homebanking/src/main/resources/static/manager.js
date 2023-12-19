const {createApp}= Vue

let app = createApp({
    data(){
        return{
            data: [],
            clients:[],
            name:"",
            lastname:"",
            email:"",
        }
    },
    created(){
        this.loanData()
    },

    methods:{
        loanData(){
            axios.get("http://localhost:8080/api/clients/all")
            .then(response =>{ this.data = response
            this.clients =response})
          .catch(error => console.log(error))
        },
        
        addClient(){
            console.log(this.lastname)
            axios.post("http://localhost:8080/api/clients",{
                "name": this.name,
                "lastname": this.lastname,
                "email": this.email,

            })
            .then(response => {this.loanData()})
            .catch(error => console.log(error))

        }

    }

    
}).mount('#app')