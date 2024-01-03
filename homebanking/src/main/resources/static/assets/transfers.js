const {createApp}= Vue


let app = createApp({
    data(){
        return{
            clients: [],
            accounts: [],
            amount: "",
            description: "",
            accountOrigen: "",
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
            this.accounts =response.data.accounts.number})
          .catch(error => console.log(error))
        },
        createTransfer(){
            axios.post("/api/transactions?amount="+this.amount+"&description="+this.description+"&accountOrigen="+this.accountOrigen+"&accountDestino="+this.accountDestino)
            .then(response =>{console.log(response)
            window.location.href="/assets/accounts.html"})
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