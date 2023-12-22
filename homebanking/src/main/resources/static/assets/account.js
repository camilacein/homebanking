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
     
        
    },

    methods:{
        loadData(){
            axios("/api/clients/current")
            .then(response =>{ 
                this.clients = response.data
                console.log(this.clients)
            this.accounts =response.data.accounts.find(account => account.id == this.id)
            console.log(this.accounts)
            this.transactions = this.accounts.transaction})
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