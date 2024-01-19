const {createApp}= Vue


let app = createApp({
    data(){
        return{
            clients: [],
            accounts:[],
          accountNumber: "",
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
                this.accounts= response.data.accounts
                console.log(this.clients)})
          .catch(error => console.log(error))
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
        deleteAccount(accountNumber){
            axios.patch("/api/accounts/"+ this.accountNumber)
            .then(response => {console.log(response)
            if(response.status.toString().startsWith('2')) {
                this.successMsg();
                this.statusTransaction = true
            } else {
                this.errorMsg();
                this.statusTransaction = false
            }})
            .catch(error => console.log(error))

        },
        errorMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "error",
                title: "Oops...",
                text: "No se pudo eliminar la cuenta",
            })
        },
        successMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "success",
                title: "¡Bien!",
                text: "Cuenta eliminada con exito",
            })},

        

    }

    
}).mount('#app')