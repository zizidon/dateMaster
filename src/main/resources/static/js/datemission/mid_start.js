/**
 * 
 */document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('missionForm');

    form.addEventListener('submit', function(event) {
        // すべてのミッション項目を取得
        const missions = form.querySelectorAll('.mission-item');
        let allMissionsSelected = true;

        // 各ミッションのラジオボタンの選択状況をチェック
        missions.forEach(mission => {
            const radioButtons = mission.querySelectorAll('input[type="radio"]');
            const isSelected = Array.from(radioButtons).some(radio => radio.checked);

            // 選択されていないミッションがある場合
            if (!isSelected) {
                allMissionsSelected = false;
                mission.style.borderColor = 'red';
            } else {
                mission.style.borderColor = '#ddd';
            }
        });

        // すべてのミッションが選択されていない場合
        if (!allMissionsSelected) {
            event.preventDefault(); // フォーム送信を阻止
            alert('すべてのミッションの達成状況を選択してください。');
        }
    });
});