<template>
    <v-card>
        <v-card-title>
            Blood pressure test
        </v-card-title>
        <v-card-text>
            Please enter the results from your blood pressure examination below
        </v-card-text>
        <v-card-text>
            <v-form v-model="valid">
            <v-row class="ml-4 mr-4">



                <v-text-field
                    label="Diastolic pressure"
                    hint="Valid example: 120 mmHg"
                    v-model="diastolic"
                    @keypress="isDiastolicNumber($event)"
                    :rules="[(v) => v > 60 || 'A minimum diastolic value is 60 mmHg',
                    (v) => v < 200 || 'A maximum diastolic value is 200 mmHg'
                    ]"
                >
                </v-text-field>

            </v-row>
            <v-row class="ml-4 mr-4">
                <v-text-field
                    label="Systolic pressure"
                    hint="Valid example: 80 mmHg"
                    @keypress="isSystolicNumber($event)"
                    v-model="systolic"
                    :rules="[(v) => v > 60 || 'A minimum systolic value is 60 mmHg',
                    (v) => v < 200 || 'A maximum systolic value is 200 mmHg']"
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
    name: "BloodPressureTest",
    data(){
      return{
          diastolic: '',
          systolic: '',
          valid: false,
      }
    },
    methods: {

        saveResults(){
            let text = 'Blood pressure test:\nSystolic pressure: ' + this.systolic + ' mmHg\nDiastolic pressure: ' + this.diastolic + ' mmHg\n'
            this.$emit('bloodPressureTestSaved', text)
        },

        isDiastolicNumber(e) {
            if(this.diastolic.length === 3){
              e.preventDefault()
              return
            }
            let char = String.fromCharCode(e.keyCode);
            if (/^[0-9]+$/.test(char)){
                return true;
            }
            else e.preventDefault();
        },
        isSystolicNumber(e){
            if(this.systolic.length === 3){
                e.preventDefault()
                return
            }
            let char = String.fromCharCode(e.keyCode);
            if (/^[0-9]+$/.test(char)){
                return true;
            }
            else e.preventDefault();
        }
    },

}
</script>

<style scoped>

</style>