const {createApp}= Vue


let app = createApp({
    data(){
        return{
            clients: [],
          cards:[],	
          selectedColor: "",
          selectedType: "",
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
            this.cards =response.data.cards})
          .catch(error => console.log(error))
        },
    
        createLoan(){
            let body={name:this.name,maxAmount:this.amount,interest:this.interest,payments:this.payments}
            axios.post("/api/clients/current/loan/"+this.body)
            .then(response =>{console.log(response)
                if(response.status.toString().startsWith('2')) {
                    this.successMsg();
                    this.statusTransaction = true
                } else {
                    this.errorMsg();
                    this.statusTransaction = false
                }
            window.location.href="/assets/cards.html"})
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
        errorMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "error",
                title: "Oops...",
                text: "No se pudo crear el prestamo",
            })
        },
        successMsg(){
            Swal.fire({
                background: "white",
                color: "black",
                icon: "success",
                title: "¡Bien!",
                text: "Prestamo creado con exito",
            })},

        

    }

    
}).mount('#app')