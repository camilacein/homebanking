const {createApp}= Vue

let app = createApp({
    data(){
        return{
            clients: [],
            accounts: [],
            loans: [],	
        }
    },
    created(){
        this.loanData()
        this.formatBudget()
    },

    methods:{
        loanData(){
            axios.get("/api/clients/current")
            .then(response =>{ 
                this.clients = response.data
                console.log(this.clients)
            this.accounts =response.data.accounts
            this.loans =response.data.clienLoanDTOS})
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