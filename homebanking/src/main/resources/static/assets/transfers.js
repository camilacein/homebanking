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
                text: "No se pudo crear la tarjeta",
            })
        },
        successMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "success",
                title: "¡Bien!",
                text: "Tarjeta creada con exito",
            })},
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