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
            this.transactions = this.accounts.transactionsDTO})
          .catch(error => console.log(error))
        },
        createTransfer(){
            axios.post("/api/transactions?amount="+this.amount+"&description="+this.description+"&accountOrigen="+this.accountOrigen+"&accountDestino="+this.accountDestino)
            .then(response =>{console.log(response)
                if(response.status.toString().startsWith('2')) {
                    this.successMsg();
                    this.statusTransaction = true
                } else {
                    this.errorMsg();
                    this.statusTransaction = false
                }
            window.location.href="/assets/accounts.html"})
            .catch(error => console.log(error))
        },
        errorMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "error",
                title: "Oops...",
                text: "No se pudo realizar la transferencia",
            })
        },
        successMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "success",
                title: "¡Bien!",
                text: "Transferencia realizada con exito",
            })},
       
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