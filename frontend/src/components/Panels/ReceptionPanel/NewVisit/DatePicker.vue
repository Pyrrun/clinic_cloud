<template>
        <v-date-picker
            v-model="date"
            elevation="10"
            color="indigo"
        >
        </v-date-picker>
</template>

<script>

export default {

    name: 'DatePicker',
    props:{
        isScheduleAlreadyDeclared: Boolean,
        calendarDate: String,
        editionModeEnabled: Boolean
    },
    data() {
        return {
            date: ''
        }
    },
    watch: {
        date() {
            if(this.editionModeEnabled){
                this.date = this.calendarDate
                return
            }
            if (this.date) {
                this.$store.state.date = this.date
                this.$emit('dateChanged', this.date)
            }
        },
        calendarDate(date){
            if(date){
                this.date = date
            }
            else{
                this.date = ''
            }


        }


    },
    methods: {},

}
</script>

