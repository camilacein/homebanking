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
            this.loans =response.data.clienLoan})
          .catch(error => console.log(error))
        },
        createAccount() {
           
                Swal.fire({
                    title: "Elige que tipo de cuenta quieres crear",
                    text: "Selecciona entre Ahorro o Corriente",
                    icon: "question",
                    showCancelButton: true,
                    confirmButtonColor: "#E6A51D",
                    cancelButtonColor: "#E6A51D",
                    confirmButtonText: "SAVINGS",
                    cancelButtonText: "CHECKING",
                  }).then((result) => {
                    if (result.isConfirmed) {
                        axios.post("/api/clients/current/accounts?accountType=SAVINGS")
                        .then(response => { 
                            this.loanData()
                            console.log(response)
                        }).catch(e => console.log(e))
                    }else{
                        axios.post("/api/clients/current/accounts?accountType=CHECKING")
                        .then(response => { 
                            this.loanData()
                        console.log(response)
                        }).catch(e => console.log(e))
    
                    }
                  });
    
        },
        errorMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "error",
                title: "Oops...",
                text: "No se pudo crear la cuenta",
            })
        },
        successMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "success",
                title: "¡Bien!",
                text: "Cuenta creada con exito",
            })
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