<template>
    <div>
        <ReceptionPanel v-if="userRole === 'RECEPTION_WORKER'"></ReceptionPanel>
        <DoctorPanel v-if="userRole === 'DOCTOR'"></DoctorPanel>
    </div>
</template>

<script>
import ReceptionPanel from "@/components/Panels/ReceptionPanel/ReceptionPanel";
 import DoctorPanel from "@/components/Panels/DoctorPanel/DoctorPanel";

export default {
    name: "PanelSelector",
    components: {ReceptionPanel, DoctorPanel},
    computed: {
        userRole(){
            return localStorage.getItem('role')
        }
    },
    created(){
        this.$store.commit('initializeWebSocketConnection')
    }
}
</script>

<style scoped>
.slide-enter-active,
.slide-leave-active{
    transition: all 1s;
}

.slide-enter, .slide-leave-to{
    opacity: 0
}
</style>