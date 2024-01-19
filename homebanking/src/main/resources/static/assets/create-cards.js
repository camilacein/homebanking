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
        createCard(){
            axios.post("/api/clients/current/cards?cardType="+this.selectedType+"&colors="+this.selectedColor)
            .then(response =>{console.log(response)
                if(response.status.toString().startsWith('2')) {
                    this.successMsg();
                    this.statusTransaction = true
                } else {
                    this.errorMsg();
                    this.statusTransaction = false
                }
           setTimeout(() => window.location.href="/assets/cards.html", 500)})
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

        

    }

    
}).mount('#app')