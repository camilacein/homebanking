const {createApp}= Vue

let app = createApp({
    data(){
        return{
            clients: [],
            accounts: [],
        }
    },
    created(){
        this.loanData()
        this.formatBudget()
    },

    methods:{
        loanData(){
            axios("/api/clients/1")
            .then(response =>{ 
                this.clients = response.data
                console.log(this.clients)
            this.accounts =response.data.accounts})
          .catch(error => console.log(error))
        },
        formatBudget(balance) {
            if (balance !== undefined && balance !== null) {
                return balance.toLocaleString("en-US", {
                    style: "currency",
                    currency: "ARS",
                    minimumFractingDigits: 0,
                })
            }
        },
        

    }

    
}).mount('#app')