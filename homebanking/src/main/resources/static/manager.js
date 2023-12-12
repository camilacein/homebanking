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
            axios("http://localhost:8080/clients")
            .then(response =>{ this.data = response
            this.clients =response.data._embedded.clients})
          .catch(error => console.log(error))
        },
        
        addClient(){
            console.log(this.lastname)
            axios.post("http://localhost:8080/clients",{
                "name": this.name,
                "lastname": this.lastname,
                "email": this.email,

            })
            .then(response => {this.loanData()})
            .catch(error => console.log(error))

        }

    }

    
}).mount('#app')