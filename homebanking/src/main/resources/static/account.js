const {createApp}= Vue

let app = createApp({
    data(){
        return{
            clients:[],
            accounts: [],
            transactions: [],
            id: 0,
        }
    },
    created(){
        const search = location.search
        const params = new URLSearchParams(search)
        this.formatBudget()
        this.id = params.get('id')
        console.log("/accounts/id")

        this.loadData()
        this.loanData()
        
    },

    methods:{
        loadData(){
            axios("/api/clients/1")
            .then(response =>{ 
                this.clients = response.data
                console.log(this.clients)
            this.accounts =response.data.accounts.find(account => account.id == this.id)})
          .catch(error => console.log(error))
        },
        loanData(){
            axios.get("/api/accounts/"+this.id+"/transactions")
            .then(response =>{ 
               this.transactions= response.data
                console.log(response)
                
            })
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