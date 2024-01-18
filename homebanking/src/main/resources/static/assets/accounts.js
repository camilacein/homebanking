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
            axios.post("/api/clients/current/accounts")
                .then(response => {
                    // La cuenta se ha creado con éxito
                    console.log(response);
                    if(response.status.toString().startsWith('2')) {
                        this.successMsg();
                        this.statusTransaction = true
                    } else {
                        this.errorMsg();
                        this.statusTransaction = false
                    }
        
                    // Ejecutar SweetAlert centrado después de que la cuenta se haya creado
                
                })
                .catch(error => {
                    // Manejar errores
                    console.log(error);
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