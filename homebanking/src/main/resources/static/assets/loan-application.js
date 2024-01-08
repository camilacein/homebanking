const {createApp}= Vue

let app = createApp({
    data(){
        return{
            clients:[],
            accounts: [],
            transactions: [],
            id: 0,
            amount:"",
            payments:"",
            destinoAccount:"",
            loans:[],
            paymentsFilter:[],
        }
    },
    created(){
       

        this.loadData()
        this.load()
        
     
        
    },

    methods:{
       loadData(){
            axios("/api/clients/current")
            .then(response =>{ 
                this.clients = response.data
                console.log(this.clients)
            this.accounts =response.data.accounts
            console.log(this.accounts)
            })
          .catch(error => console.log(error))
        },
        Payments() {
            console.log(this.id)
            
            const filter = this.loans.filter(loan => { return this.id == loan.id })[0]
            this.paymentsFilter = filter.payments
        },
        load(){
            axios.get("/api/loans")
            .then(response =>{
                this.loans = response.data
                console.log(response)
            })
            .catch(error => console.log(error))
        },
        createLoan(){
            let body={id:this.id,amount:this.amount,payments:this.payments,destinoAccount:this.destinoAccount}
            console.log(body)
            axios.post("/api/loans",body)
            .then(response =>{console.log(response)
            window.location.href="/assets/accounts.html"})
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
        formatDateTime(array){
            const options = {day:'numeric', month:'long', year:'numeric', hour:'numeric', minute:'numeric', second:'numeric'}
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