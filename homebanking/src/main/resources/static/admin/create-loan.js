const {createApp}= Vue


let app = createApp({
    data(){
        return{
         name: "",
         maxAmount:"",
         payments:"",
         interest:"",

        }
    },
    created(){
      
    },

    methods:{
       
        createLoan(){
            let paymentsArray = this.payments.split(",").map(Number)
            let body={
                name:this.name,
                maxAmount:this.maxAmount,
                interest:this.interest,
                payments:paymentsArray}
            axios.post("/api/loans/create",body)
            .then(response =>{console.log(response)
                if(response.status.toString().startsWith('2')) {
                    this.successMsg();
                    this.statusTransaction = true
                } else {
                    this.errorMsg();
                    this.statusTransaction = false
                }})
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