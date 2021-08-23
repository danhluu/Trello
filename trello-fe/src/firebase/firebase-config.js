import firebase from 'firebase/app';
import 'firebase/storage'

// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyBKaDtS_nPGGkREhhwmBTtyvQg4wvVRAFM",
    authDomain: "trello-b8056.firebaseapp.com",
    projectId: "trello-b8056",
    storageBucket: "trello-b8056.appspot.com",
    messagingSenderId: "973580270766",
    appId: "1:973580270766:web:2dedabd9b1e0953ef8813f",
    measurementId: "G-8Q4BNZ3MRM"
};
firebase.initializeApp(firebaseConfig);

const storage = firebase.storage();

export {storage, firebase as default };