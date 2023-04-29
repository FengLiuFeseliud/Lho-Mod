public VoxelShape makeShape(){
	VoxelShape shape = VoxelShapes.empty();
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.0625, 0, 0.125, 0.9375, 0.125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.0625, 0.875, 0.125, 0.9375, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.0625, 0.875, 1, 0.9375, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.0625, 0, 1, 0.9375, 0.125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 1, 0.0625, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.9375, 0, 1, 1, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.1875, 1, 0.3125, 0.25, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.375, 1, 0.3125, 0.4375, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.5625, 1, 0.3125, 0.625, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.75, 1, 0.3125, 0.8125, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.1875, 0, 0.875, 0.25, 0));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.375, 0, 0.875, 0.4375, 0));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.5625, 0, 0.875, 0.625, 0));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.75, 0, 0.875, 0.8125, 0));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.1875, 0.125, 0, 0.25, 0.3125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.375, 0.125, 0, 0.4375, 0.3125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.5625, 0.125, 0, 0.625, 0.3125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.75, 0.125, 0, 0.8125, 0.3125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(1, 0.1875, 0.6875, 1, 0.25, 0.875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(1, 0.375, 0.6875, 1, 0.4375, 0.875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(1, 0.5625, 0.6875, 1, 0.625, 0.875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(1, 0.75, 0.6875, 1, 0.8125, 0.875));

	return shape;
}