<template>
  <v-card-text>
    <v-row class="text-center mt-3">
      <v-col class="mb-4">
        <h2 class="display-1 font-weight-bold mb-3">
          Patient registration form
        </h2>
        <transition name="fade">
          <p style="color: red" v-if="errorPopped">{{ error }}</p>
        </transition>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="10" align="center">
        <v-form ref="form" v-model="valid" class="login">
          <v-row justify="center">
            <v-col cols="12">
              <v-text-field
                type="email"
                autocomplete="off"
                :rules="emailRules"
                label="E-mail address"
                error-count="1"
                required
                v-model="email"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col cols="6">
              <v-text-field
                @keypress="isLetter($event)"
                label="First name"
                :rules="[(v) => !!v || 'Please enter a first name.']"
                required
                v-model="firstName"
                type="text"
              ></v-text-field>
            </v-col>
            <v-col cols="6">
              <v-text-field
                @keypress="isLetter($event)"
                label="Last name"
                :rules="[(v) => !!v || 'Please enter a last name.']"
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
                :rules="[(v) => !!v || 'Please enter a phone number.']"
                required
                v-model="phone"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="6">
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
                  <v-btn text color="primary" @click="modal = false">
                    Cancel
                  </v-btn>
                  <v-btn text color="primary" @click="$refs.dialog.save(date)">
                    OK
                  </v-btn>
                </v-date-picker>
              </v-dialog>
            </v-col>
            <v-col cols="6">
              <v-text-field
                label="PESEL"
                :rules="[
                  (v) => !!v || 'Please enter a PESEL number',
                  (v) =>
                    v.length === 11 ||
                    'PESEL number requires exactly 11 digits',
                ]"
                min="11"
                required
                v-model="pesel"
                v-mask="'###########'"
              >
              </v-text-field>
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col cols="6" align="center">
              <v-text-field
                ref="address"
                v-model="address"
                :rules="[
                  () => !!address || 'This field is required',
                  () =>
                    address.length <= 25 ||
                    'Address must be less than 25 characters',
                  (v) =>
                    /(?=.*[A-Za-z]{3})/.test(v) ||
                    'The address should contain at least 3 letters.',
                ]"
                label="Address Line"
                placeholder="Address"
                counter="25"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="6" align="center">
              <v-text-field
                v-mask="'#####'"
                ref="address"
                type="number"
                v-model="flatNumber"
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
          <v-col cols="12" class="mb-3" align="center">
            <v-btn
              color="primary"
              @click="registerNewPatient"
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
  name: "Step1",
  data() {
    return {
      countries: ["Poland", "England"],
      firstName: "",
      lastName: "",
      secondName: "",
      address: "",
      flatNumber: "",
      city: "",
      postcode: "",
      country: "",
      phone: "",
      pesel: "",
      email: "",
      buttonEnabled: false,
      valid: false,
      errorPopped: "",
      date: new Date().toISOString().substr(0, 10),
      modal: false,
      allowedDates: (val) => val <= new Date().toISOString().substr(0, 10),
      emailRules: [
        (v) => !!v || "Please enter an e-mail address.",
        (v) =>
          /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/.test(
            v
          ) || "Correct e-mail example: example@example.com",
      ],
    };
  },
  methods: {
    registerNewPatient() {
      const patient = {
        firstName: this.firstName,
        lastName: this.lastName,
        secondName: this.secondName,
        street: this.address,
        flatNumber: this.flatNumber,
        city: this.city,
        postcode: this.postcode,
        country: this.country,
        phone: this.phone,
        birthdate: this.date,
        pesel: this.pesel,
        email: this.email,
      };
      this.$emit("firstStepFinished", patient);
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
      this.buttonEnabled = this.valid;
    },
  },
  watch: {
    valid() {
      setTimeout(this.showButton, 200);
    },
  },
};
</script>

<style scoped>
</style>