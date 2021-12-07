<template>
    <v-card>
        <v-card-title>
            Pulse check
        </v-card-title>
        <v-card-text>
            Please enter the results from your pulse examination below
        </v-card-text>
        <v-card-text>
            <v-form v-model="valid">
                    <v-text-field
                        label="Pulse"
                        hint="Valid example: 130 units/minute"
                        v-model="pulse"
                        @keypress="isNumber($event)"
                        :rules="[(v) => v > 30 || 'A minimum pulse value is 30 units/minute',
                    (v) => v < 800 || 'A maximum pulse value is 200 units/minute'
                    ]"
                    >
                    </v-text-field>
            </v-form>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="blue darken-1"
                text
                @click="savePulse"
                :disabled="!valid"
            >
                Save
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    name: "PulseCheck",
    data(){
        return{
            pulse: '',
            valid: false,
        }
    },
    methods:{
        isNumber(e){
            if(this.pulse.length === 3){
                e.preventDefault()
                return
            }
            let char = String.fromCharCode(e.keyCode);
            if (/^[0-9]+$/.test(char)){
                return true;
            }
            else e.preventDefault();
        },
        savePulse(){
            let text = 'Pulse check:\n' + this.pulse + ' units/minute\n'
            this.$emit('pulseSaved', text)
        }
    }
}
</script>

<style scoped>

</style>