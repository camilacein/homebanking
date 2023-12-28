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
        formatDate(array){
            const options = {day:'numeric', month:'long', year:'numeric'}
            const release = new Date(array.creationDate)
            return release.toLocaleDateString("en-US",options)
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