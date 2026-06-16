class VoiceRecorder {
    constructor(buttonElement, onComplete) {
        this.mediaRecorder = null;
        this.audioChunks = [];
        this.isRecording = false;
        this.onComplete = onComplete;
        this.button = buttonElement;
        this.setupButton();
    }
    setupButton() {
        this.button.addEventListener('mousedown', () => this.startRecording());
        this.button.addEventListener('mouseup', () => this.stopRecording());
        this.button.addEventListener('mouseleave', () => { if (this.isRecording) this.stopRecording(); });
        this.button.addEventListener('touchstart', (e) => { e.preventDefault(); this.startRecording(); });
        this.button.addEventListener('touchend', (e) => { e.preventDefault(); this.stopRecording(); });
    }
    async startRecording() {
        try {
            const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
            this.mediaRecorder = new MediaRecorder(stream, { mimeType: 'audio/webm' });
            this.audioChunks = [];
            this.mediaRecorder.ondataavailable = (e) => { if (e.data.size > 0) this.audioChunks.push(e.data); };
            this.mediaRecorder.onstop = () => {
                const audioBlob = new Blob(this.audioChunks, { type: 'audio/webm' });
                stream.getTracks().forEach(t => t.stop());
                if (this.onComplete) this.onComplete(audioBlob);
            };
            this.mediaRecorder.start();
            this.isRecording = true;
            this.button.classList.add('btn-danger');
            this.button.textContent = '🔴 Recording...';
        } catch (e) { console.error('Microphone access denied', e); }
    }
    stopRecording() {
        if (this.mediaRecorder && this.isRecording) {
            this.mediaRecorder.stop();
            this.isRecording = false;
            this.button.classList.remove('btn-danger');
            this.button.textContent = '🎤 Record Voice';
        }
    }
}
