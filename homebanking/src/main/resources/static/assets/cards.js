const {createApp}= Vue


let app = createApp({
    data(){
        return{
            clients: [],
          cards:[],	
          selectedColor: "",
          selectedType: "",
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
        createCard(){
            axios.post("/api/clients/current/cards?colors="+this.selectedColor+"&cardType="+this.selectedType)
            .then(response =>console.log(response))
            .catch(error => console.log(error))
        },
        logout() {
            axios.post("/api/logout")
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "/index.html"
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        },

        

    }

    
}).mount('#app')