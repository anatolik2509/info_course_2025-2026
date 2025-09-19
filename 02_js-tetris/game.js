const HEIGHT_IN_SQUARES = 15
const WIDTH_IN_SQUARES = 10

const figures = [
    [[-1, 0], [0, 0], [1, 0], [0, -1]],
    [[0, 0], [0, 1], [1, 0], [1, 1]],
    [[-1, 0], [0, 0], [1, 0], [1, 1]],
    [[-1, 0], [0, 0], [1, 0], [1, -1]],
    [[-1, 0], [0, 0], [1, 0], [2, 0]],
    [[0, 0], [-1, 0], [0, 1], [1, 1]],
    [[0, 0], [-1, 0], [0, -1], [1, -1]],
]

const colors = ['red', 'yellow', 'blue', 'greenyellow', 'orange', 'purple', 'cyan']

let figureState = null
let elementMap = []
let blockMap = []
let colorMap = []
let points = 0
let startTime
let timer

initGameScreen()
document.addEventListener('keydown', handleKeyboardEvent)

window.onload = function() {
    startTime = Date.now()
    startTimer()
    startGameCycle()
    
}

function startTimer() {
    let time = document.getElementById("time")
    timer = setInterval(() => {
        let currentTime = Date.now()
        millis = currentTime - startTime
        const date = new Date(null)
        date.setMilliseconds(millis)
        const timer = date.toISOString().slice(14, 19)
        time.textContent = timer
    }, 1000)
}

function initGameScreen() {
    let gameScreen = document.getElementById("game")
    let gameField = document.getElementById("game-screen")
    gameField.style.gridTemplateColumns = `repeat(${WIDTH_IN_SQUARES}, 1fr)`
    let squareLen = Math.floor(Math.min(gameScreen.clientWidth / WIDTH_IN_SQUARES, gameScreen.clientHeight / HEIGHT_IN_SQUARES))
    gameField.style.height = squareLen * HEIGHT_IN_SQUARES + 'px'
    gameField.style.width = squareLen * WIDTH_IN_SQUARES + 'px'
    for (let x = 0; x < WIDTH_IN_SQUARES; x++) {
        elementMap.push([])
        blockMap.push([])
        colorMap.push([])
        for (let y = 0; y < HEIGHT_IN_SQUARES; y++) {
            elementMap[x].push(null)
            blockMap[x].push(false)
            colorMap[x].push('white')
        }
    }
    for (let y = 0; y < HEIGHT_IN_SQUARES; y++) {
        for (let x = 0; x < WIDTH_IN_SQUARES; x++) {
            square = document.createElement("div")
            square.classList.add('square')
            square.style.height = squareLen - 2 + 'px'
            square.style.width = squareLen - 2 + 'px'
            gameField.appendChild(square)
            elementMap[x][y] = square
        }
    }
}

function startGameCycle() {
    figureState = {
        figure: figures[Math.floor(Math.random() *  figures.length)],
        figurePos: [Math.floor(Math.random() * (WIDTH_IN_SQUARES - 4)) + 2, -1],
        figureColor: colors[Math.floor(Math.random() *  colors.length)],
        hello: function () {
            console.log('hello')
        }
    }
    startFigureFalling(figureState)
}

function startFigureFalling(state) {
    cycle = setInterval(() => {
        newState = moveFigure(state, 0, 1)
        if (allowedState(newState)) {
            renderState(state, newState)
            state.figurePos = newState.figurePos
        } else {
            clearInterval(cycle)
            freezeFigure(state)
            if (figureUnderTop(state)) {
                startGameCycle()
                cleanLines()
            }
            else {
                clearInterval(timer)
                gameOver()
            }
        }
    }, 1000)
}

function figureUnderTop(state) {
    for (let i = 0; i < state.figure.length; i++) {
        if (state.figurePos[1] < 0) {
            return false
        }
    }
    return true
}

function renderState(oldState, newState) {
    for (let i = 0; i < oldState.figure.length; i++) {
        let pointPos = [oldState.figure[i][0] + oldState.figurePos[0], oldState.figure[i][1] + oldState.figurePos[1]]
        addColorOnSquare(pointPos, 'white')
    }
    for (let i = 0; i < newState.figure.length; i++) {
        let pointPos = [newState.figure[i][0] + newState.figurePos[0], newState.figure[i][1] + newState.figurePos[1]]
        addColorOnSquare(pointPos, newState.figureColor)
    }
}

function addColorOnSquare(squarePos, color) {
    if (squarePos[1] < HEIGHT_IN_SQUARES && squarePos[1] >= 0) {
        elementMap[squarePos[0]][squarePos[1]].style.backgroundColor = color
    }
}

function allowedState(state) {
    for (let i = 0; i < state.figure.length; i++) {
        let pointPos = [state.figure[i][0] + state.figurePos[0], state.figure[i][1] + state.figurePos[1]]
        if (pointPos[0] < 0 || pointPos[0] >= WIDTH_IN_SQUARES || pointPos[1] >= HEIGHT_IN_SQUARES) {
            return false;
        }
        if (pointPos[1] >= 0) {
            if (blockMap[pointPos[0]][pointPos[1]]) {
                return false
            }
        }
    }
    return true
}

function freezeFigure(state) {
    for (let i = 0; i < state.figure.length; i++) {
        let pointPos = [state.figure[i][0] + state.figurePos[0], state.figure[i][1] + state.figurePos[1]]
        blockMap[pointPos[0]][pointPos[1]] = true
        colorMap[pointPos[0]][pointPos[1]] = state.figureColor
    }
}

function cleanLines() {
    let lines = []
    for (var y = HEIGHT_IN_SQUARES - 1; y >= 0; y--) {
        let wholeLine = true
        for (var x = 0; x < WIDTH_IN_SQUARES; x++) {
            wholeLine = wholeLine && blockMap[x][y]
        }
        if (wholeLine) {
            lines.push(y)
            points += 100
        }
    }
    let passedLines = 0
    for (var y = HEIGHT_IN_SQUARES - 1; y >= 0; y--) {
        if (lines.includes(y - passedLines)) {
            passedLines++
            y++
            continue
        }
        for (var x = 0; x < WIDTH_IN_SQUARES; x++) {
            blockMap[x][y] = y - passedLines < 0 ? false : blockMap[x][y - passedLines]
            colorMap[x][y] = y - passedLines < 0 ? 'black' : colorMap[x][y - passedLines]
            addColorOnSquare([x, y], colorMap[x][y])
        }
    }
    updatePoints()
}

function updatePoints() {
    let gameOverScreen = document.getElementById("score")
    gameOverScreen.textContent = points
}

function moveFigure(state, dx, dy) {
    console.log(JSON.stringify(state))
    newState = JSON.parse(JSON.stringify(state));
    newState.figurePos[0] = state.figurePos[0] + dx
    newState.figurePos[1] = state.figurePos[1] + dy
    return newState
}

function gameOver() {
    let gameOverScreen = document.getElementById("game-over-screen")
    gameOverScreen.style.display = 'block'
}

function handleKeyboardEvent(event) {
    if (event.key == 'ArrowDown') {
        handleDown()
    }
    if (event.key == 'ArrowUp') {
        handleRotate(transformRight)
    }
    if (event.key == 'ArrowLeft') {
        handleMove(-1)
    }
    if (event.key == 'ArrowRight') {
        handleMove(1)
    }
}

function handleMove(dx) {
    if (figureState == null) {
        return
    }
    newState = moveFigure(figureState, dx, 0)
    if (allowedState(newState)) {
        renderState(figureState, newState)
        figureState.figurePos = newState.figurePos
    }
}

function handleDown() {
    if (figureState == undefined) {
        return
    }
    newState = moveFigure(figureState, 0, 1)
    if (allowedState(newState)) {
        renderState(figureState, newState)
        figureState.figurePos = newState.figurePos
    }
}

function handleRotate(transformFunc) {
    if (figureState == undefined) {
        return
    }
    newState = tryRotate(figureState, transformFunc)
    if (allowedState(newState)) {
        renderState(figureState, newState)
        figureState.figure = newState.figure
    }
}

function tryRotate(state, transformFunc) {
    newState = JSON.parse(JSON.stringify(state));
    newFigure = transformFunc(state.figure)
    newState.figure = newFigure
    return newState
}

function transformRight(figure) {
    let newFigure = []
    for (let i = 0; i < figure.length; i++) {
        newFigure.push([figure[i][1] * -1, figure[i][0]])
    }
    return newFigure
}
