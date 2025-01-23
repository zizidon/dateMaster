/**
 * 
 */document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('missionForm');

    form.addEventListener('submit', function(event) {
        const missions = form.querySelectorAll('.mission-item');
        let allMissionsSelected = true;

        missions.forEach(mission => {
            const radioButtons = mission.querySelectorAll('input[type="radio"]');
            const isSelected = Array.from(radioButtons).some(radio => radio.checked);

            if (!isSelected) {
                allMissionsSelected = false;
                mission.style.borderColor = 'red';
            } else {
                mission.style.borderColor = '#ddd';
            }
        });

        if (!allMissionsSelected) {
            event.preventDefault();
            alert('すべてのミッションの達成状況を選択してください。');
        }
    });
});