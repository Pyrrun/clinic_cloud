<template>
    <v-card>
        <v-card-title>
            Weight and height test
        </v-card-title>
        <v-card-text>
            Please enter the results from your weight and height examination below
        </v-card-text>
        <v-card-text>
            <v-form v-model="valid">
                <v-row class="ml-4 mr-4">

                    <v-text-field
                        label="Weight"
                        hint="Valid example: 80 kg"
                        v-model="weight"
                        @keypress="isWeightNumber($event)"
                        :rules="[(v) => v > 10 || 'A minimum weight value is 10 kg',
                    (v) => v < 800 || 'A maximum weight value is 800 kg'
                    ]"
                    >
                    </v-text-field>

                </v-row>
                <v-row class="ml-4 mr-4">
                    <v-text-field
                        label="Height"
                        hint="Valid example: 170 cm"
                        @keypress="isHeightNumber($event)"
                        v-model="height"
                        :rules="[(v) => v > 60 || 'A minimum height value is 40 cm',
                    (v) => v < 300 || 'A maximum height value is 300 cm']"
                    >
                    </v-text-field>
                </v-row>
            </v-form>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="blue darken-1"
                text
                @click="saveResults"
                :disabled="!valid"
            >
                Save
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    name: "WeightAndHeight",
    data(){
        return {
            weight: '',
            height: '',
            valid: false,

        }
    },
    methods: {
        saveResults(){
            let text = 'Weight: ' + this.weight + ' kg\n' + 'Height: ' + this.height + ' cm\n'
            this.$emit('weightAndHeightSaved', text)
        },

        isWeightNumber(e) {
            if(this.weight.length === 3){
                e.preventDefault()
                return
            }
            let char = String.fromCharCode(e.keyCode);
            if (/^[0-9]+$/.test(char)){
                return true;
            }
            else e.preventDefault();
        },
        isHeightNumber(e){
            if(this.height.length === 3){
                e.preventDefault()
                return
            }
            let char = String.fromCharCode(e.keyCode);
            if (/^[0-9]+$/.test(char)){
                return true;
            }
            else e.preventDefault();
        }
    }

}
</script>

<style scoped>

</style>