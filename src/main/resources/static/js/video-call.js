let localStream = null;
let remoteStream = null;
let peerConnection = null;
let isMuted = false;
let isCameraOff = false;
const configuration = { iceServers: [{ urls: 'stun:stun.l.google.com:19302' }] };
const roomId = document.getElementById('roomId')?.value;
const userId = document.getElementById('userId')?.value;
const userName = document.getElementById('userName')?.value;
const socket = new WebSocket('ws://' + window.location.host + '/ws-call?userId=' + userId);
socket.onopen = () => {
    socket.send(JSON.stringify({ type: 'join', roomId, userId }));
    startLocalStream();
};
socket.onmessage = async (event) => {
    const data = JSON.parse(event.data);
    switch (data.type) {
        case 'user-joined': await createOffer(); break;
        case 'offer': await handleOffer(data); break;
        case 'answer': await handleAnswer(data); break;
        case 'ice-candidate': await handleIceCandidate(data); break;
        case 'end': endCall(); break;
    }
};
async function startLocalStream() {
    try {
        localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
        document.getElementById('localVideo').srcObject = localStream;
        document.getElementById('callStatus').textContent = 'Connected';
        document.getElementById('remoteWaiting').style.display = 'block';
    } catch (e) { console.error('Error accessing media', e); }
}
async function createOffer() {
    peerConnection = new RTCPeerConnection(configuration);
    peerConnection.onicecandidate = (e) => {
        if (e.candidate) socket.send(JSON.stringify({ type: 'ice-candidate', roomId, userId, candidate: e.candidate }));
    };
    peerConnection.ontrack = (e) => {
        document.getElementById('remoteVideo').srcObject = e.streams[0];
        document.getElementById('remoteWaiting').style.display = 'none';
    };
    localStream.getTracks().forEach(t => peerConnection.addTrack(t, localStream));
    const offer = await peerConnection.createOffer();
    await peerConnection.setLocalDescription(offer);
    socket.send(JSON.stringify({ type: 'offer', roomId, userId, sdp: peerConnection.localDescription }));
}
async function handleOffer(data) {
    peerConnection = new RTCPeerConnection(configuration);
    peerConnection.onicecandidate = (e) => {
        if (e.candidate) socket.send(JSON.stringify({ type: 'ice-candidate', roomId, userId, candidate: e.candidate }));
    };
    peerConnection.ontrack = (e) => {
        document.getElementById('remoteVideo').srcObject = e.streams[0];
        document.getElementById('remoteWaiting').style.display = 'none';
    };
    localStream.getTracks().forEach(t => peerConnection.addTrack(t, localStream));
    await peerConnection.setRemoteDescription(new RTCSessionDescription(data.sdp));
    const answer = await peerConnection.createAnswer();
    await peerConnection.setLocalDescription(answer);
    socket.send(JSON.stringify({ type: 'answer', roomId, userId, sdp: peerConnection.localDescription }));
}
async function handleAnswer(data) {
    if (peerConnection) await peerConnection.setRemoteDescription(new RTCSessionDescription(data.sdp));
}
async function handleIceCandidate(data) {
    if (peerConnection && data.candidate) {
        try { await peerConnection.addIceCandidate(new RTCIceCandidate(data.candidate)); }
        catch (e) { console.error('Error adding ICE candidate', e); }
    }
}
function toggleMute() {
    if (localStream) {
        isMuted = !isMuted;
        localStream.getAudioTracks().forEach(t => t.enabled = !isMuted);
        document.getElementById('muteBtn').textContent = isMuted ? '🔊 Unmute' : '🔇 Mute';
    }
}
function toggleCamera() {
    if (localStream) {
        isCameraOff = !isCameraOff;
        localStream.getVideoTracks().forEach(t => t.enabled = !isCameraOff);
        document.getElementById('cameraBtn').textContent = isCameraOff ? '📷 Camera On' : '📷 Camera Off';
    }
}
function toggleFullscreen() {
    const video = document.getElementById('remoteVideo');
    if (video.requestFullscreen) video.requestFullscreen();
}
function endCall() {
    if (peerConnection) peerConnection.close();
    if (localStream) localStream.getTracks().forEach(t => t.stop());
    socket.send(JSON.stringify({ type: 'end', roomId, userId }));
    window.location.href = '/messages';
}
