const parkingBlocks = document.querySelectorAll('.parking-block');
const parkingSpaceInput = document.getElementById('parking-space');
const selectedSpotInput = document.getElementById('selected-spot');
const availableSpacesInput = document.getElementById('available-spaces');

// 모든 주차 공간 비활성화
function disableAllParkingBlocks() {
    parkingBlocks.forEach(block => {
        block.classList.add('disable');
        block.classList.remove('available');
    });
}

// 주차 공간 활성화
// function enableParkingBlocks(availableSpaces) {
//     parkingBlocks.forEach(block => {
//         if (availableSpaces.includes(block.dataset.value)) {
//             block.classList.remove('disable'); // 비활성화 상태 제거
//             block.classList.add('available'); // 활성화 상태 추가
//         }
//     });
// }

// 주차 공간 활성화
function enableParkingBlocks(availableSpaces) {
    parkingBlocks.forEach(block => {
        // availableSpaces 배열에서 각 블록의 data-value와 일치하는지 확인
        if (availableSpaces.some(space => space.location === block.dataset.value)) {
            block.classList.remove('disable'); // 'disable' 클래스 제거
            block.classList.add('available'); // 'available' 클래스 추가
        } else {
            block.classList.remove('available'); // 'disable' 클래스 제거
            block.classList.add('disable'); // 'available' 클래스 추가
        }
    });
}

// 주차 공간 클릭 이벤트
parkingBlocks.forEach(block => {
    block.addEventListener('click', () => {
        if (!block.classList.contains('available')) return; // Only allow selecting available blocks
        // Remove 'selected' from all blocks
        parkingBlocks.forEach(b => b.classList.remove('selected'));
        // Add 'selected' to clicked block
        block.classList.add('selected');
        // Update hidden input value
        parkingSpaceInput.value = block.dataset.value;
        // Display selected parking space in label
        selectedSpotInput.value = block.dataset.value;
    });
});

// 사용 가능한 주차 공간 확인 버튼 클릭 이벤트
document.getElementById('checkAvailabilityButton').addEventListener('click', async () => {
    const datetime = document.getElementById('datetime').value;
    const duration = document.getElementById('duration').value;

    if (!datetime || !duration) {
        alert('예약 날짜와 시간을 입력하세요.');
        return;
    }

    try {
        // 요청 본문으로 보낼 데이터 객체 생성
        const requestData = {
            start: datetime,
            duration: duration
        };
        const response = await fetch('/api/parking/available-spaces', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData) // JSON 문자열로 변환하여 전송
        });

        if (!response.ok) throw new Error('네트워크 오류 발생');

        const availableSpaces = await response.json();
        const count = availableSpaces.length;

        // 사용 가능한 주차 공간 수 표시
        availableSpacesInput.value = `${count}개 사용 가능`;

        // 모든 주차 공간 비활성화
        disableAllParkingBlocks();

        // 받아온 주차 공간 활성화
        enableParkingBlocks(availableSpaces);
    } catch (error) {
        console.error('오류:', error);
        availableSpacesInput.value = '오류 발생: 사용 가능한 주차 공간을 가져오는 데 실패했습니다.';
    }
});

// 예약하기 버튼 클릭 이벤트
document.getElementById('reserveButton').addEventListener('click', (event) => {
    event.preventDefault(); // 기본 제출 동작 방지
    const datetime = document.getElementById('datetime').value; // 예약 날짜 및 시간
    const duration = document.getElementById('duration').value; // 사용 시간
    const selectedParkingSpace = parkingSpaceInput.value; // 선택된 주차 공간

    if (!selectedParkingSpace) {
        alert('주차 공간을 선택해주세요.');
        return;
    }

    // 요청 URL에 쿼리 파라미터로 데이터 추가
    const queryParams = new URLSearchParams({
        start: datetime,
        duration: duration,
        parkingSpace: selectedParkingSpace,
    }).toString();

    // 예약 요청을 서버로 전송
    fetch(`/api/parking/reservation?${queryParams}`, {
        method: 'POST', // POST 메소드 사용
        headers: {
            'Content-Type': 'application/json' // 여전히 JSON이지만, 본문은 비워둘 수 있습니다.
        },
        body: null,
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.text();
        })
        .then(() => {
            alert("예약 성공:"); // 성공 메시지 출력
            // 리다이렉트 URL 설정
            window.location.href = "/reservation/confirmation?" + queryParams; // 리다이렉트
        })
        .catch(error => {
            console.error('오류:', error.message);
            alert('예약 중 오류가 발생했습니다: ' + error.message); // 오류 메시지 출력
        });
});
// 페이지 로드 시 모든 주차 공간 비활성화
disableAllParkingBlocks();
