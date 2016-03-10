package com.jeff.game.pathfinder.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.jeff.game.pathfinder.graph.Graph
import com.jeff.game.pathfinder.graph.GraphBuilder
import com.jeff.game.pathfinder.graph.Marker
import com.jeff.game.pathfinder.graph.MarkerType
import com.jeff.game.pathfinder.grid.Cell
import com.jeff.game.pathfinder.grid.Grid
import com.jeff.game.pathfinder.util.option.None
import com.jeff.game.pathfinder.util.option.Option
import com.jeff.game.pathfinder.util.option.Some
import java.util.NoSuchElementException


class GraphScene(inputFileName: String, val camera: Camera) : Screen {

    private val grid: Grid
    private val graph: Graph
    private val fileHandle: FileHandle
    private val manager = AssetManager()
    private val batch = SpriteBatch()

    private val cellReg: Texture
    private val cellImpass: Texture
    private val cellPath: Texture
    private val cellTele: Texture
    private val cellStart: Texture
    private val cellStop: Texture
    private val cellTouched: Texture
    private val w = Gdx.graphics.width
    private val h = Gdx.graphics.height
    private val topY: Float
    private val cellW: Float
    private val cellH: Float

    private var start: Option<Cell> = None()
    private var stop: Option<Cell> = None()
    private var font: BitmapFont

    init {
        fileHandle = Gdx.files.internal(inputFileName)
        grid = Grid.of(fileHandle.file())
        graph = GraphBuilder.from(grid)
        manager.load("cell-reg.jpg", Texture::class.java)
        manager.load("cell-impass.jpg", Texture::class.java)
        manager.load("cell-path.jpg", Texture::class.java)
        manager.load("cell-tele.jpg", Texture::class.java)
        manager.load("cell-start.jpg", Texture::class.java)
        manager.load("cell-stop.jpg", Texture::class.java)
        manager.load("cell-touched.jpg", Texture::class.java)
        manager.finishLoading()

        cellReg = manager.get("cell-reg.jpg")
        cellImpass = manager.get("cell-impass.jpg")
        cellPath = manager.get("cell-path.jpg")
        cellTele = manager.get("cell-tele.jpg")
        cellStart = manager.get("cell-start.jpg")
        cellStop = manager.get("cell-stop.jpg")
        cellTouched = manager.get("cell-touched.jpg")
        val vertCount = grid.grid.size
        val horiCount = grid.grid[0].size
        cellW = w / horiCount.toFloat()
        cellH = h / vertCount.toFloat()
        topY = h - cellH
        font = BitmapFont(Gdx.files.internal("smallfonter.fnt"))
    }

    fun clicked(x: Int, y: Int) {
        var vec = Vector3(x.toFloat(), y.toFloat(), 0f)
        camera.unproject(vec)

        val cell: Option<Cell> = try {
            Some(grid.grid.flatten().first { underClick(vec.x, vec.y, it.y, it.x) })
        } catch(e: NoSuchElementException) {
            None()
        }

        if (cell.defined()) {
            if (start.defined() && stop.defined()) {
                graph.clearMarkers()
                start = None()
                stop = None()
            } else if (!start.defined()) {
                if (cell.get().cost != -1) {
                    start = cell
                    graph.graph[Pair(start.get().y, start.get().x)]!!.marker = Some(Marker(MarkerType.SOURCE, 0.0, None(), false))
                }
            } else {
                stop = cell
                graph.findPath(Pair(start.get().y, start.get().x), Pair(stop.get().y, stop.get().x))
            }
        }

    }

    private fun onScreenPos(y: Int, x: Int): Pair<Float, Float> {
        return Pair(cellW * x, topY - (cellH * y))
    }

    private fun underClick(mouseX: Float, mouseY: Float, cellY: Int, cellX: Int): Boolean {
        val leftCorner = onScreenPos(cellY, cellX)
        if (mouseX >= leftCorner.first && mouseY >= leftCorner.second) {
            if (mouseY <= leftCorner.second + cellH) {
                if (mouseX <= leftCorner.first + cellW) {
                    return true
                } else {
                    return false
                }
            } else {
                return false
            }
        } else {
            return false
        }
    }

    private fun pickTexture(cell: Cell): Texture {
        var texture: Texture
        if (cell.cost == -1) {
            texture = cellImpass
        } else if (cell.teleTo.defined()) {
            texture = cellReg
        } else {
            val node = graph.graph[(Pair(cell.y, cell.x))]!!
            if (node.marker.defined()) {
                val mark = node.marker.get()
                texture = when {
                    mark.type == MarkerType.SOURCE -> cellStart
                    mark.type == MarkerType.DEST -> cellStop
                    mark.type == MarkerType.REGULAR -> cellReg
                    mark.type == MarkerType.VISITED -> cellTouched
                    mark.type == MarkerType.PATH -> cellPath
                    else -> throw IllegalStateException()
                }
            } else {
                texture = cellReg
            }
        }
        return texture
    }

    override fun show() {

    }

    override fun pause() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun hide() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        val cells = grid.grid
        batch.begin()
        cells.forEach { line ->
            line.forEach { cell ->
                val p = onScreenPos(cell.y, cell.x)
                batch.draw(pickTexture(cell), p.first, p.second, cellW, cellH)
                if (cell.cost != -1) {
                    val name = if(!cell.teleTo.defined()){
                        "${cell.cost}"
                    }else{
                        "${cell.name}"
                    }
                    val layout = GlyphLayout(font, name)
                    font.draw(batch, layout, p.first + (cellW - layout.width) / 2.0f, p.second + (cellH + layout.height) / 2.0f)
                }
            }
        }
        batch.end()
    }

    override fun resume() {

    }

    override fun dispose() {

    }
}