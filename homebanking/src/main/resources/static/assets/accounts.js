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
        createAccount(){
            axios.post("/api/clients/current/accounts")
            .then(response =>console.log(response))
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