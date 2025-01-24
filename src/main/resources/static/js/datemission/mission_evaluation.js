document.addEventListener('DOMContentLoaded', function() {
    const evaluationElement = document.querySelector('.date-evaluation h2');
    const backButton = document.querySelector('.button-group button');

    // 評価結果の色付けと強調
    if (evaluationElement) {
        const evaluationText = evaluationElement.textContent.trim();
        
        switch (evaluationText) {
            case '素晴らしい':
                evaluationElement.style.color = '#4CAF50';
                break;
            case '良い':
                evaluationElement.style.color = '#2196F3';
                break;
            case '普通':
                evaluationElement.style.color = '#FFC107';
                break;
            case '改善の余地あり':
                evaluationElement.style.color = '#FF9800';
                break;
            case '要注意':
                evaluationElement.style.color = '#F44336';
                break;
            default:
                evaluationElement.style.color = '#333';
        }
    }

    // ボタンにホバーエフェクト
    if (backButton) {
        backButton.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.05)';
        });

        backButton.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
        });
    }
});