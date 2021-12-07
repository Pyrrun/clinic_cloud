<template>
    <v-card>
        <v-card-title>
            Auscultation
        </v-card-title>
        <v-card-text>
            Please choose the result of your auscultation examination
        </v-card-text>
        <v-card-text>
            <v-checkbox
                label="Bronchial murmur"
                value="Bronchial murmur"
                v-model="selected"
                color="red"
                hide-details
            ></v-checkbox>
            <v-checkbox
                label="Bubble murmur"
                v-model="selected"
                color="blue"
                value="Bubble murmur"
                hide-details
            ></v-checkbox>
            <v-checkbox
                v-model="selected"
                label="Rales"
                color="violet"
                value="Rales"
                hide-details
            ></v-checkbox>
            <v-checkbox
                v-model="selected"
                label="Stridor"
                color="yellow"
                value="Stridor"
                hide-details
            ></v-checkbox>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text :disabled="selected.length === 0" @click="saveAusculation">Save
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    name: "Auscultation",
    data(){
        return{
           selected: []
        }
    },
    methods: {
        saveAusculation(){
            let text = 'Ausculation:\n' + this.selected + '\n'
            this.$emit('ausculationSaved', text)

        }
    }
}
</script>

<style scoped>

</style>