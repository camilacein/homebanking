const {createApp}= Vue

let app = createApp({
    data(){
        return{
            clients: [],
          cards:[],	
        }
    },
    created(){
        this.loanData()
    },

    methods:{
        loanData(){
            axios("/api/clients/current")
            .then(response =>{ 
                this.clients = response.data
                console.log(this.clients)
            this.cards =response.data.cardDTOS})
          .catch(error => console.log(error))
        },

        

    }

    
}).mount('#app')