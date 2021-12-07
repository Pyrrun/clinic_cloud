<template>
    <v-card>
        <v-card-title>
            Patient removal
        </v-card-title>
        <v-card-text>
            Are you sure you want to remove <b>{{ patientData.firstName }}
            {{ patientData.lastName }}</b> from your patient list?
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="closeDeleteDialog">
                Cancel
            </v-btn>
            <v-btn color="blue darken-1" text @click="deletePatient">Yes,
                I'm sure
            </v-btn>
            <v-spacer></v-spacer>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    name: "PatientDelete",
    props: {
        patientData: Object,
    },
    methods: {
        closeDeleteDialog() {
            this.$emit('deleteDialogClosed')
        },
        deletePatient() {
            this.$emit('patientDeleteSubmitted')
        }
    },

}
</script>

<style scoped>

</style>