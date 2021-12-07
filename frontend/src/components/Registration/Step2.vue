<template>
    <v-card-text>
        <v-row class="text-center">
            <v-col class="mb-4">
                <h2 class="display-1 font-weight-bold mb-3">
                    Step 2
                </h2>
                <h3 class="headline font-weight-thin mb-3">
                    <i>Personal data</i>
                </h3>
                <transition name="fade">
                    <p style="color:red;" v-if="errorPopped">{{ error }}</p>
                </transition>
            </v-col>
        </v-row>
        <v-row justify="center">
            <v-col lg="10" xs="12" align="center">
                <v-form ref="form" v-model="valid" class="login">
                    <v-row justify="center">
                        <v-col cols="6">

                            <v-text-field
                                @keypress="isLetter($event)"
                                label="First name"
                                :rules="[v => !!v || 'Please enter your first name.']"
                                required
                                v-model="firstName"
                                type="text"
                            ></v-text-field>
                        </v-col>
                        <v-col cols="6">
                            <v-text-field
                                @keypress="isLetter($event)"
                                label="Last name"
                                :rules="[v => !!v || 'Please enter your last name.']"
                                required
                                v-model="lastName"
                            ></v-text-field>
                        </v-col>
                    </v-row>
                    <v-row justify="center">
                        <v-col cols="6" align="center">
                            <v-text-field
                                @keypress="isLetter($event)"
                                label="Second name (optional)"
                                v-model="secondName"
                            ></v-text-field>
                        </v-col>
                        <v-col cols="6">
                            <v-text-field
                                @keypress="isNumber($event)"
                                label="Phone"
                                :rules="[v => !!v || 'Please enter your phone number.']"
                                required
                                v-model="phone"
                            ></v-text-field>
                        </v-col>
                    </v-row>
                    <v-col cols="12">
                        <v-dialog
                            ref="dialog"
                            v-model="modal"
                            :return-value.sync="date"
                            persistent
                            width="290px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="date"
                                    label="Birthdate"
                                    prepend-icon="mdi-calendar"
                                    readonly
                                    v-bind="attrs"
                                    v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker
                                v-model="date"
                                scrollable
                                :allowed-dates="allowedDates"
                            >
                                <v-spacer></v-spacer>
                                <v-btn
                                    text
                                    color="primary"
                                    @click="modal = false"
                                >
                                    Cancel
                                </v-btn>
                                <v-btn
                                    text
                                    color="primary"
                                    @click="$refs.dialog.save(date)"
                                >
                                    OK
                                </v-btn>
                            </v-date-picker>
                        </v-dialog>

                    </v-col>

                    <v-row justify="center">
                        <v-col cols="6" align="center">
                            <v-text-field
                                ref="address"
                                v-model="address"
                                :rules="[
                            () => !!address || 'This field is required',
                            () => address.length <= 25 || 'Address must be less than 25 characters',
                            v => /(?=.*[A-Za-z]{3})/.test(v) || 'Your address should contain at least 3 letters.',
                            ]"

                                label="Address Line"
                                placeholder="Address"
                                counter="25"
                                required
                            ></v-text-field>
                        </v-col>
                        <v-col cols="6" align="center">
                            <v-text-field
                                ref="address"
                                v-mask="'#####'"
                                type="number"
                                v-model="address_2"
                                label="Address Line No.2 (house number)"
                                placeholder="Address"
                                counter="5"
                            ></v-text-field>
                        </v-col>
                        <v-col cols="6">
                            <v-text-field
                                ref="zip"
                                v-mask="'##-###'"
                                v-model="postcode"
                                :rules="[() => !!postcode || 'This field is required']"
                                label="ZIP / Postal Code"
                                required
                                placeholder="Postal code"
                            ></v-text-field>
                        </v-col>
                        <v-col cols="6">
                            <v-text-field
                                @keypress="isLetter($event)"
                                ref="city"
                                v-model="city"
                                :rules="[() => !!city || 'This field is required']"
                                label="City"
                                placeholder="City"
                                required
                            ></v-text-field>
                        </v-col>
                        <v-col cols="12">
                            <v-select
                                @keypress="isLetter($event)"
                                v-model="country"
                                :rules="[() => !!country || 'This field is required']"
                                :items="countries"
                                label="Country"
                                placeholder="Select..."
                                required
                            ></v-select>
                        </v-col>
                    </v-row>
                </v-form>
                <v-row justify="center">
                    <v-col cols="12" align="center">
                        <v-btn
                            color="primary"
                            @click="validateForm"
                            :disabled="!buttonEnabled"
                            large
                            block
                        >Submit
                        </v-btn>
                    </v-col>
                </v-row>
            </v-col>
        </v-row>
    </v-card-text>


</template>

<script>
export default {
    data() {
        return {
            countries: ['Polska', 'England'],
            firstName: '',
            lastName: '',
            secondName: '',
            address: '',
            address_2: '',
            city: '',
            postcode: '',
            country: '',
            phone: '',
            buttonEnabled: false,
            valid: false,
            errorPopped: '',
            date: new Date().toISOString().substr(0, 10),
            modal: false,
            allowedDates: val => val <= new Date().toISOString().substr(0, 10),
        }
    },
    methods: {
        validateForm() {
            this.$emit('secondStepComplete', {
                'firstName': this.firstName,
                'lastName': this.lastName,
                'secondName': this.secondName,
                'street': this.address,
                'flatNumber': this.address_2,
                'city': this.city,
                'postcode': this.postcode,
                'country': this.country,
                'phone': this.phone,
                'birthdate': this.date,
            })

            this.$toast.success('Step 2 completed successfully.')
        },
        isLetter(e) {
            let char = String.fromCharCode(e.keyCode);
            if (/^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$/.test(char)) return true;
            else e.preventDefault();
        },
        isNumber(e) {
            let char = String.fromCharCode(e.keyCode);
            if (/^[0-9]+$/.test(char)) return true;
            else e.preventDefault();
        },
        showButton() {
            this.buttonEnabled = this.valid
        },

    },
    watch: {
        valid() {
            console.log(this.valid)
            setTimeout(this.showButton, 200);
        }
    },
}
</script>